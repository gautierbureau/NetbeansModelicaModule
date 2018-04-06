package org.myorg.modelicafiletype.lexer;

import java.util.*;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

public class SJLanguageHierarchy extends LanguageHierarchy<SJTokenId> {

    private static List<SJTokenId> tokens;
    private static Map<Integer, SJTokenId> idToToken;

    private static void init() {
        tokens = Arrays.<SJTokenId>asList(new SJTokenId[]{
new SJTokenId("EOF", "whitespace", 0),
new SJTokenId("WHITESPACE", "whitespace", 1),
new SJTokenId("SINGLE_LINE_COMMENT", "comment", 4),
new SJTokenId("FORMAL_COMMENT", "comment", 5),
new SJTokenId("MULTI_LINE_COMMENT", "comment", 6),
new SJTokenId("ABSTRACT", "keyword", 8),
new SJTokenId("ASSERT", "keyword", 9),
new SJTokenId("BOOLEAN", "keyword", 10),
new SJTokenId("BREAK", "keyword", 11),
new SJTokenId("BYTE", "keyword", 12),
new SJTokenId("CASE", "keyword", 13),
new SJTokenId("CATCH", "keyword", 14),
new SJTokenId("CHAR", "keyword", 15),
new SJTokenId("CLASS", "keyword", 16),
new SJTokenId("CONST", "keyword", 17),
new SJTokenId("CONTINUE", "keyword", 18),
new SJTokenId("DO", "keyword", 20),
new SJTokenId("DOUBLE", "keyword", 21),
new SJTokenId("ELSE", "keyword", 22),
new SJTokenId("ENUM", "keyword", 23),
new SJTokenId("EXTENDS", "keyword", 24),
new SJTokenId("FALSE", "keyword", 25),
new SJTokenId("FINAL", "keyword", 26),
new SJTokenId("FINALLY", "keyword", 27),
new SJTokenId("FLOAT", "keyword", 28),
new SJTokenId("FOR", "keyword", 29),
new SJTokenId("GOTO", "keyword", 30),
new SJTokenId("IF", "keyword", 31),
new SJTokenId("IMPLEMENTS", "keyword", 32),
new SJTokenId("IMPORT", "keyword", 33),
new SJTokenId("INSTANCEOF", "keyword", 34),
new SJTokenId("INT", "keyword", 35),
new SJTokenId("INTERFACE", "keyword", 36),
new SJTokenId("LONG", "keyword", 37),
new SJTokenId("NATIVE", "keyword", 38),
new SJTokenId("NEW", "keyword", 39),
new SJTokenId("NULL", "keyword", 40),
new SJTokenId("PACKAGE", "keyword", 41),
new SJTokenId("PRIVATE", "keyword", 42),
new SJTokenId("PROTECTED", "keyword", 43),
new SJTokenId("PUBLIC", "keyword", 44),
new SJTokenId("RETURN", "keyword", 45),
new SJTokenId("SHORT", "keyword", 46),
new SJTokenId("STATIC", "keyword", 47),
new SJTokenId("STRICTFP", "keyword", 48),
new SJTokenId("SUPER", "keyword", 49),
new SJTokenId("SWITCH", "keyword", 50),
new SJTokenId("SYNCHRONIZED", "keyword", 51),
new SJTokenId("THIS", "keyword", 52),
new SJTokenId("THROW", "keyword", 53),
new SJTokenId("THROWS", "keyword", 54),
new SJTokenId("TRANSIENT", "keyword", 55),
new SJTokenId("TRUE", "keyword", 56),
new SJTokenId("TRY", "keyword", 57),
new SJTokenId("VOID", "keyword", 58),
new SJTokenId("VOLATILE", "keyword", 59),
new SJTokenId("WHILE", "keyword", 60),
new SJTokenId("WITHIN", "keyword", 61),
new SJTokenId("ENCAPSULATED", "keyword", 62),
new SJTokenId("FUNCTION", "keyword", 63),
new SJTokenId("INPUT", "keyword", 64),
new SJTokenId("END", "keyword", 65),
new SJTokenId("EXTERNAL", "keyword", 66),
new SJTokenId("PARAMETER", "keyword", 67),
new SJTokenId("EQUATION", "keyword", 68),
new SJTokenId("STRING", "string", 69),
new SJTokenId("REAL", "string", 70),
new SJTokenId("BOOLEAN_MAJ", "string", 71),
new SJTokenId("ENUMERATION", "keyword", 72),
new SJTokenId("TYPE", "keyword", 73),
new SJTokenId("THEN", "keyword", 74),
new SJTokenId("WHEN", "keyword", 75),
new SJTokenId("ELSEWHEN", "keyword", 76),
new SJTokenId("OR", "keyword", 77),
new SJTokenId("AND", "keyword", 78),
new SJTokenId("MODEL", "keyword", 79),
new SJTokenId("REDECLARE", "keyword", 80),
new SJTokenId("CONSTANT", "keyword", 81),
new SJTokenId("COMPLEX", "method-declaration", 82),
new SJTokenId("NOEVENT", "method-declaration", 83),
new SJTokenId("PRE", "method-declaration", 84),
new SJTokenId("LOGEVENT", "method-declaration", 85),
new SJTokenId("CONJ", "method-declaration", 86),
new SJTokenId("REALFUNC", "method-declaration", 87),
new SJTokenId("IMAG", "method-declaration", 88),
new SJTokenId("SIN", "method-declaration", 89),
new SJTokenId("COS", "method-declaration", 90),
new SJTokenId("TAN", "method-declaration", 91),
new SJTokenId("ATAN", "method-declaration", 92),
new SJTokenId("CONNECTOR", "keyword", 93),
new SJTokenId("ICON", "method-declaration", 94),
new SJTokenId("RECTANGLE", "method-declaration", 95),
new SJTokenId("INTEGER", "string", 96),
new SJTokenId("RECORD", "keyword", 97),
new SJTokenId("DISCRETE", "keyword", 98),
new SJTokenId("ALGORITHM", "keyword", 99),
new SJTokenId("CONSTRAINEDBY", "keyword", 100),
new SJTokenId("EXPANDABLE", "keyword", 101),
new SJTokenId("FLOW", "keyword", 102),
new SJTokenId("INITIAL", "keyword", 103),
new SJTokenId("NOT", "keyword", 104),
new SJTokenId("DER", "keyword", 105),
new SJTokenId("INNER", "keyword", 106),
new SJTokenId("PARTIAL", "keyword", 107),
new SJTokenId("REPACEABLE", "keyword", 108),
new SJTokenId("ANNOTATION", "keyword", 109),
new SJTokenId("CONNECT", "keyword", 110),
new SJTokenId("OUTER", "keyword", 111),
new SJTokenId("OUTPUT", "keyword", 112),
new SJTokenId("BLOCK", "keyword", 113),
new SJTokenId("ELSEIF", "keyword", 114),
new SJTokenId("IN", "keyword", 115),
new SJTokenId("PURE", "keyword", 116),
new SJTokenId("IMPURE", "keyword", 117),
new SJTokenId("LOGEVENT1", "method-declaration", 118),
new SJTokenId("LOGEVENT2", "method-declaration", 119),
new SJTokenId("LOGEVENT3", "method-declaration", 120),
new SJTokenId("LOGEVENT4", "method-declaration", 121),
new SJTokenId("LOGEVENT5", "method-declaration", 122),
new SJTokenId("INTEGER_LITERAL", "number", 123),
new SJTokenId("DECIMAL_LITERAL", "number", 124),
new SJTokenId("HEX_LITERAL", "number", 125),
new SJTokenId("OCTAL_LITERAL", "number", 126),
new SJTokenId("FLOATING_POINT_LITERAL", "number", 127),
new SJTokenId("DECIMAL_FLOATING_POINT_LITERAL", "number", 128),
new SJTokenId("DECIMAL_EXPONENT", "number", 129),
new SJTokenId("HEXADECIMAL_FLOATING_POINT_LITERAL", "number", 130),
new SJTokenId("HEXADECIMAL_EXPONENT", "number", 131),
new SJTokenId("CHARACTER_LITERAL", "literal", 132),
new SJTokenId("STRING_LITERAL", "literal", 133),
new SJTokenId("IDENTIFIER", "identifier", 134),
new SJTokenId("LETTER", "literal", 135),
new SJTokenId("PART_LETTER", "literal", 136),
new SJTokenId("LPAREN", "operator", 137),
new SJTokenId("RPAREN", "operator", 138),
new SJTokenId("LBRACE", "operator", 139),
new SJTokenId("RBRACE", "operator", 140),
new SJTokenId("LBRACKET", "operator", 141),
new SJTokenId("RBRACKET", "operator", 142),
new SJTokenId("SEMICOLON", "operator", 143),
new SJTokenId("COMMA", "operator", 144),
new SJTokenId("DOT", "operator", 145),
new SJTokenId("AT", "operator", 146),
new SJTokenId("GUILLEMETDOUBLE", "operator", 147),
new SJTokenId("GUILLEMETSIMPLE", "operator", 148),
new SJTokenId("BACKSLASH", "operator", 149),
new SJTokenId("ASSIGN", "operator", 150),
new SJTokenId("LT", "operator", 151),
new SJTokenId("BANG", "operator", 152),
new SJTokenId("TILDE", "operator", 153),
new SJTokenId("HOOK", "operator", 154),
new SJTokenId("COLON", "operator", 155),
new SJTokenId("EQ", "operator", 156),
new SJTokenId("LE", "operator", 157),
new SJTokenId("GE", "operator", 158),
new SJTokenId("NE", "operator", 159),
new SJTokenId("SC_OR", "operator", 160),
new SJTokenId("SC_AND", "operator", 161),
new SJTokenId("INCR", "operator", 162),
new SJTokenId("DECR", "operator", 163),
new SJTokenId("PLUS", "operator", 164),
new SJTokenId("MINUS", "operator", 165),
new SJTokenId("STAR", "operator", 166),
new SJTokenId("SLASH", "operator", 167),
new SJTokenId("BIT_AND", "operator", 168),
new SJTokenId("BIT_OR", "operator", 169),
new SJTokenId("XOR", "operator", 170),
new SJTokenId("REM", "operator", 171),
new SJTokenId("LSHIFT", "operator", 172),
new SJTokenId("PLUSASSIGN", "operator", 173),
new SJTokenId("MINUSASSIGN", "operator", 174),
new SJTokenId("STARASSIGN", "operator", 175),
new SJTokenId("SLASHASSIGN", "operator", 176),
new SJTokenId("ANDASSIGN", "operator", 177),
new SJTokenId("ORASSIGN", "operator", 178),
new SJTokenId("XORASSIGN", "operator", 179),
new SJTokenId("REMASSIGN", "operator", 180),
new SJTokenId("LSHIFTASSIGN", "operator", 181),
new SJTokenId("RSIGNEDSHIFTASSIGN", "operator", 182),
new SJTokenId("RUNSIGNEDSHIFTASSIGN", "operator", 183),
new SJTokenId("ELLIPSIS", "operator", 184),
new SJTokenId("GT", "operator", 185),
new SJTokenId("DIFFGIT", "operator", 186)
        });
        idToToken = new HashMap<Integer, SJTokenId>();
        for (SJTokenId token : tokens) {
            idToToken.put(token.ordinal(), token);
        }
    }

    static synchronized SJTokenId getToken(int id) {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<SJTokenId> createTokenIds() {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    @Override
    protected synchronized Lexer<SJTokenId> createLexer(LexerRestartInfo<SJTokenId> info) {
        return new SJLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-modelica";
    }

}
