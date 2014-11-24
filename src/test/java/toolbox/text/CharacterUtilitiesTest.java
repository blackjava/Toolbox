package toolbox.text;

import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterUtilitiesTest {

    @Test
    public void single_quote_is_recognized_as_quote() {
        assertTrue(CharacterUtilities.isQuote('\''));
    }

    @Test
    public void double_quote_is_recognized_as_quote() {
        assertTrue(CharacterUtilities.isQuote('"'));
    }

    @Test
    public void symbol_is_not_recognized_as_quote() {
        assertFalse(CharacterUtilities.isQuote('.'));
    }

    @Test
    public void opening_parenthesis_is_recognized() {
        assertTrue(CharacterUtilities.isOpeningParenthesis('('));
    }

    @Test
    public void symbol_is_not_recognized_as_opening_parenthesis() {
        assertFalse(CharacterUtilities.isOpeningParenthesis('.'));
    }
    
    @Test
    public void closing_parenthesis_is_recognized() {
        assertTrue(CharacterUtilities.isClosingParenthesis(')'));
    }

    @Test
    public void symbol_is_not_recognized_as_closing_parenthesis() {
        assertFalse(CharacterUtilities.isClosingParenthesis('.'));
    }

    @Test
    public void opening_square_bracket_is_recognized() {
        assertTrue(CharacterUtilities.isOpeningSquareBracket('['));
    }

    @Test
    public void symbol_is_not_recognized_as_opening_square_bracket() {
        assertFalse(CharacterUtilities.isOpeningSquareBracket('.'));
    }

    @Test
    public void closing_square_bracket_is_recognized() {
        assertTrue(CharacterUtilities.isClosingSquareBracket(']'));
    }

    @Test
    public void symbol_is_not_recognized_as_closing_square_bracket() {
        assertFalse(CharacterUtilities.isClosingSquareBracket('.'));
    }

    @Test
    public void opening_curly_bracket_is_recognized() {
        assertTrue(CharacterUtilities.isOpeningCurlyBracket('{'));
    }

    @Test
    public void symbol_is_not_recognized_as_opening_curly_bracket() {
        assertFalse(CharacterUtilities.isOpeningCurlyBracket('.'));
    }

    @Test
    public void closing_curly_bracket_is_recognized() {
        assertTrue(CharacterUtilities.isClosingCurlyBracket('}'));
    }

    @Test
    public void symbol_is_not_recognized_as_closing_curly_bracket() {
        assertFalse(CharacterUtilities.isClosingCurlyBracket('.'));
    }

    @Test
    public void letter_is_not_recognized_as_symbol() {
        assertFalse(CharacterUtilities.isSymbol('a'));
    }

    @Test
    public void symbol_is_recognized() {
        assertTrue(CharacterUtilities.isSymbol('*'));
    }
    
    @Test
    public void digit_is_not_recognized_as_symbol() {
        assertFalse(CharacterUtilities.isSymbol('1'));
    }

    @Test
    public void whitespace_is_not_recognized_as_symbol() {
        assertFalse(CharacterUtilities.isSymbol(' '));
    }
}
