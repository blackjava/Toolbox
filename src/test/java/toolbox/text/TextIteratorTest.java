package toolbox.text;

import org.junit.Test;
import static org.junit.Assert.*;

public class TextIteratorTest {

    @Test
    public void null_string_does_not_have_next_token() {
        TextIterator iterator = new TextIterator(null);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void empty_string_does_not_have_next_token() {
        TextIterator iterator = new TextIterator("");
        assertFalse(iterator.hasNext());
    }

    @Test
    public void character_string_has_next_token() {
        TextIterator iterator = new TextIterator("token");
        assertTrue(iterator.hasNext());
    }
    
    @Test
    public void whitespace_only_string_does_not_have_next_token() {
        TextIterator iterator = new TextIterator(" \t\n\r");
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void continuous_text_is_returned_in_sequence() {
        TextIterator iterator = new TextIterator("token");
        
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertNull(iterator.next());
    }

    @Test
    public void leading_and_trailing_whitespace_is_ignored() {
        TextIterator iterator = new TextIterator("\t token\n\r");
        
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertNull(iterator.next());
    }

    @Test
    public void whitespace_in_content_is_preserved() {
        TextIterator iterator = new TextIterator("_ \t\n\r_");
        
        assertEquals(Character.valueOf('_'), iterator.next());
        assertEquals(Character.valueOf(' '), iterator.next());
        assertEquals(Character.valueOf('\t'), iterator.next());
        assertEquals(Character.valueOf('\n'), iterator.next());
        assertEquals(Character.valueOf('\r'), iterator.next());
        assertEquals(Character.valueOf('_'), iterator.next());
        assertNull(iterator.next());
    }
    
    @Test
    public void regular_text_is_not_inside_quotes() {
        TextIterator iterator = new TextIterator("token");
        
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertFalse(iterator.insideQuotes());
    }

    @Test
    public void single_quoted_text_is_inside_quotes() {
        TextIterator iterator = new TextIterator("'token'");
        
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('\''), iterator.next());
        assertTrue(iterator.insideQuotes());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertTrue(iterator.insideQuotes());
        assertEquals(Character.valueOf('\''), iterator.next());
        assertFalse(iterator.insideQuotes());
    }
    
    @Test
    public void double_quoted_text_is_inside_quotes() {
        TextIterator iterator = new TextIterator("\"token\"");
        
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('"'), iterator.next());
        assertTrue(iterator.insideQuotes());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertTrue(iterator.insideQuotes());
        assertEquals(Character.valueOf('"'), iterator.next());
        assertFalse(iterator.insideQuotes());
    }

    @Test
    public void escaped_quote_is_not_counted_as_start_or_end_of_quote() {
        TextIterator iterator = new TextIterator("\\\"token\\\"");

        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('\\'), iterator.next());
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('\"'), iterator.next());
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('\\'), iterator.next());
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('\"'), iterator.next());
        assertFalse(iterator.insideQuotes());
    }

    public void double_escaped_quote_is_counted_as_start_or_end_of_quote() {
        TextIterator iterator = new TextIterator("\\\\\"token\\\\\"");
        
        assertFalse(iterator.insideQuotes());
        assertEquals(Character.valueOf('\\'), iterator.next());
        assertEquals(Character.valueOf('\\'), iterator.next());
        assertEquals(Character.valueOf('"'), iterator.next());
        assertTrue(iterator.insideQuotes());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertEquals(Character.valueOf('\\'), iterator.next());
        assertEquals(Character.valueOf('\\'), iterator.next());
        assertTrue(iterator.insideQuotes());
        assertEquals(Character.valueOf('"'), iterator.next());
        assertFalse(iterator.insideQuotes());
    }
    
    @Test
    public void regular_text_is_not_inside_parentheses() {
        TextIterator iterator = new TextIterator("token");
        
        assertFalse(iterator.insideParentheses());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertFalse(iterator.insideParentheses());
    }

    @Test
    public void text_in_parentheses_is_recognized_correctly() {
        TextIterator iterator = new TextIterator("((token))");
        
        assertFalse(iterator.insideParentheses());
        assertEquals(Character.valueOf('('), iterator.next());
        assertTrue(iterator.insideParentheses());
        assertEquals(Character.valueOf('('), iterator.next());
        assertTrue(iterator.insideParentheses());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertTrue(iterator.insideParentheses());
        assertEquals(Character.valueOf(')'), iterator.next());
        assertTrue(iterator.insideParentheses());
        assertEquals(Character.valueOf(')'), iterator.next());
        assertFalse(iterator.insideParentheses());
    }

    @Test
    public void regular_text_is_not_inside_square_brackets() {
        TextIterator iterator = new TextIterator("token");
        
        assertFalse(iterator.insideSquareBrackets());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertFalse(iterator.insideSquareBrackets());
    }

    @Test
    public void text_in_square_brackets_is_recognized_correctly() {
        TextIterator iterator = new TextIterator("[[token]]");
        
        assertFalse(iterator.insideSquareBrackets());
        assertEquals(Character.valueOf('['), iterator.next());
        assertTrue(iterator.insideSquareBrackets());
        assertEquals(Character.valueOf('['), iterator.next());
        assertTrue(iterator.insideSquareBrackets());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertTrue(iterator.insideSquareBrackets());
        assertEquals(Character.valueOf(']'), iterator.next());
        assertTrue(iterator.insideSquareBrackets());
        assertEquals(Character.valueOf(']'), iterator.next());
        assertFalse(iterator.insideSquareBrackets());
    }

    @Test
    public void regular_text_is_not_inside_curly_brackets() {
        TextIterator iterator = new TextIterator("token");
        
        assertFalse(iterator.insideCurlyBrackets());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertFalse(iterator.insideCurlyBrackets());
    }
    
    @Test
    public void text_in_curly_brackets_is_recognized_correctly() {
        TextIterator iterator = new TextIterator("{{token}}");
        
        assertFalse(iterator.insideCurlyBrackets());
        assertEquals(Character.valueOf('{'), iterator.next());
        assertTrue(iterator.insideCurlyBrackets());
        assertEquals(Character.valueOf('{'), iterator.next());
        assertTrue(iterator.insideCurlyBrackets());
        assertEquals(Character.valueOf('t'), iterator.next());
        assertEquals(Character.valueOf('o'), iterator.next());
        assertEquals(Character.valueOf('k'), iterator.next());
        assertEquals(Character.valueOf('e'), iterator.next());
        assertEquals(Character.valueOf('n'), iterator.next());
        assertTrue(iterator.insideCurlyBrackets());
        assertEquals(Character.valueOf('}'), iterator.next());
        assertTrue(iterator.insideCurlyBrackets());
        assertEquals(Character.valueOf('}'), iterator.next());
        assertFalse(iterator.insideCurlyBrackets());
    }
    
    @Test
    public void remove_throws_unsupported_operation_exception() {
        TextIterator iterator = new TextIterator("");
        
        UnsupportedOperationException exception = null;
        try {
            iterator.remove();
        } catch (UnsupportedOperationException uoe) {
            exception = uoe;
        }
        
        assertNotNull(exception);
    }

}