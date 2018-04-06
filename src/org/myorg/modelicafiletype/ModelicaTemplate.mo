within Dynamo.Electrical.Machines;

model DYNModelGeneratorPQ "Generator with pre-set active and reactive power outputs"
  /*
  The P output is modulated according to frequency (in order to model frequency containment reserves)
  The Q output is only modulated when large voltage variations occur
  */

  import Dynamo.Connectors;
  import Dynamo.Electrical.Controls.Machines.DYNModelSwitchOff;
  import Dynamo.NonElectrical.Logs.Timeline;
  import Dynamo.NonElectrical.Logs.TimelineKeys;

  extends DYNModelSwitchOff;

  public

    parameter Types.AC.ActivePower P0  "Initial active power";
    parameter Types.AC.ReactivePower Q0 "Initial reactive power";
    parameter Types.AC.VoltageModule U0  "Initial voltage module";
    parameter SIunits.Angle uTeta0  "Initial voltage angle";
    parameter Types.AC.VoltageModule UMin "Minimum voltage";
    parameter Types.AC.VoltageModule UMax "Maximum voltage";
    parameter Types.AC.ActivePower PMin "Minimum active power";
    parameter Types.AC.ActivePower PMax "Maximum active power";
    parameter Real Lambda (unit = "kV/MVAr") "Voltage sensitivity of reactive power regulation";
    parameter Real Alpha (unit = "MW/Hz") "Frequency sensitivity of FCR";
    parameter Boolean FrequencyContainmentReserve = true "Frequency containment reserve running ?";
    parameter Constants.state State0 = Constants.state.CLOSED " Initial connection state";

    Connectors.ACPower out (V (re (start = u0.re), im (start = u0.im)),i (re (start = -i0.re), im (start = -i0.im))) "Voltage and current output";
    Connectors.ImPin omegaRef "Network angular frequency";
    Connectors.BPin running (value (start = true)) "Generator running ?";
    Constants.state state (start = State0) "Generator connected ?";

  protected

    final parameter Types.AC.ApparentPower s0 = Complex (P0, Q0) "Initial apparent power";
    final parameter Types.AC.Voltage u0 (re = ComplexMath.real (U0 * ComplexMath.exp (ComplexMath.j * uTeta0)),
                                         im = ComplexMath.imag (U0 * ComplexMath.exp (ComplexMath.j * uTeta0))) "Initial complex voltage";
    final parameter Types.AC.Current i0 (re = if u0 == 0 then 0 else ComplexMath.real(u0 * ComplexMath.conj(s0) / (ComplexMath.'abs'(u0) * ComplexMath.'abs'(u0))),
                                         im = if u0 == 0 then 0 else ComplexMath.imag(u0 * ComplexMath.conj(s0) / (ComplexMath.'abs'(u0) * ComplexMath.'abs'(u0))))
                                         "Initial complex current";
    constant Types.AC.ReactivePower QDeadBand = 1e-4 "Reactive power dead-band";
    constant Types.AC.VoltageModule UDeadBand = 1e-4 "Voltage dead-band";

    Types.AC.ApparentPower S (re (start = s0.re), im (start = s0.im)) "Complex apparent power";
    Types.AC.ReactivePower Q (start = Q0) "Reactive power generated";
    Types.AC.ReactivePower QUMin (start = (UMin - U0) / Lambda);
    Types.AC.ReactivePower QUMax (start = (UMax - U0) / Lambda);
    Types.AC.ActivePower P (start = P0) "Active power generated";
    Types.AC.ActivePower PFCR (start = 0) "Active power generation due to frequency regulation";
    Types.AC.ActivePower PRaw (start = P0) "Raw active power request";
    Types.AC.VoltageModule UAbs (start = U0);
    Types.AC.VoltageModule UAbsRaw (start = U0);
    type regulation = enumeration (none, voltageDecrease, voltageIncrease) "Enumeration for voltage regulation type";
    regulation voltageRegulation (start = regulation.none) "Type of voltage regulation";

  equation

    // Basic equations linking complex numbers
    S = Complex(P, Q);
    S = - ComplexMath.conj(out.i * UAbs * UAbs / out.V);

    UAbsRaw = ComplexMath.'abs'(out.V);
    QUMin = (UMin - UAbsRaw) / Lambda;
    QUMax = (UMax - UAbsRaw) / Lambda;

    if running.value then
      // Add frequency regulation
      if FrequencyContainmentReserve then
        PFCR = Alpha * (1 - omegaRef.value);
      else
        PFCR = 0;
      end if;
      PRaw = P0 + PFCR;
      P = if noEvent(PRaw >= PMax) then PMax else if noEvent(PRaw <= PMin) then PMin else PRaw;
      Q = if voltageRegulation == regulation.voltageDecrease then QUMax else if voltageRegulation == regulation.voltageIncrease then QUMin else Q0;
      // Switch PQ/PV
      UAbs = if voltageRegulation == regulation.voltageDecrease then UMax else if voltageRegulation == regulation.voltageIncrease then UMin else UAbsRaw;
    else
      PFCR = 0;
      PRaw = 0;
      P = 0;
      Q = 0;
      UAbs = 0;
    end if;

    when switchOff and pre(running.value) then
      running.value = false;
      state = Constants.state.OPEN;
    end when;

    when pre(voltageRegulation) == regulation.voltageDecrease and Q0 < QUMax - QDeadBand or pre(voltageRegulation) == regulation.voltageIncrease and Q0 > QUMin + QDeadBand then
      voltageRegulation = regulation.none;
      Timeline.logEvent(TimelineKeys.GeneratorPQBackRegulation);
    elsewhen UAbsRaw > UMax + UDeadBand then
      voltageRegulation = regulation.voltageDecrease;
      Timeline.logEvent(TimelineKeys.GeneratorPQMaxV);
    elsewhen UAbsRaw < UMin - UDeadBand then
      voltageRegulation = regulation.voltageIncrease;
      Timeline.logEvent(TimelineKeys.GeneratorPQMinV);
    end when;

end DYNModelGeneratorPQ;
