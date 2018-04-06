/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.modelicafiletype;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.myorg.modelicafiletype.lexer.SJTokenId;

@LanguageRegistration(mimeType = "text/x-modelica")
public class SJLanguage extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return SJTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "SJ";
    }

}
