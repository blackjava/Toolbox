package toolbox.text;

/**
 * Utility functionality for working with characters.
 */
public class CharacterUtilities {
    private static CharacterUtilitiesProvider provider = new DefaultCharacterUtilities();
    
    /**
     * Determine whether or not the character is an opening curly bracket.
     * @param character the character to investigate
     * @return true if the current character is an opening curly bracket, false otherwise 
     */
    public static boolean isOpeningCurlyBracket(Character character) {
        return provider.isOpeningCurlyBracket(character);
    }
    
    /**
     * Determine whether or not the character is an opening parenthesis.
     * @param character the character to investigate
     * @return true if the current character is an opening parenthesis, false otherwise 
     */
    public static boolean isOpeningParenthesis(Character character) {
        return provider.isOpeningParenthesis(character);
    }
    
    /**
     * Determine whether or not the character is a closing curly bracket.
     * @param character the character to investigate
     * @return true if the current character is a closing curly bracket, false otherwise 
     */
    public static boolean isClosingCurlyBracket(Character character) {
        return provider.isClosingCurlyBracket(character);
    }

    /**
     * Determine whether or not the character is a closing parenthesis.
     * @param character the character to investigate
     * @return true if the current character is a closing parenthesis, false otherwise 
     */
    public static boolean isClosingParenthesis(Character character) {
        return provider.isClosingParenthesis(character);
    }
    
    /**
     * Determine whether or not the character is an opening square bracket.
     * @param character the character to investigate
     * @return true if the current character is an opening square bracket, false otherwise 
     */
    public static boolean isOpeningSquareBracket(Character character) {
        return provider.isOpeningSquareBracket(character);
    }
    
    /**
     * Determine whether or not the character is a closing square bracket.
     * @param character the character to investigate
     * @return true if the current character is a closing square bracket, false otherwise 
     */
    public static boolean isClosingSquareBracket(Character character) {
        return provider.isClosingSquareBracket(character);
    }
    
    /**
     * Determine whether or not the character is a quote.
     * @param character the character to investigate
     * @return true if the current character is a quote, false otherwise 
     */
    public static boolean isQuote(Character character) {
        return provider.isQuote(character);
    }

    /**
     * Determine whether or not the character is a symbol.
     * @param character the character to investigate
     * @return true if the current character is a symbol, false otherwise 
     */
    public static boolean isSymbol(Character character) {
        return provider.isSymbol(character);
    }

    
    /**
     * Determine whether or not the character is an escape character.
     * @param character the character to investigate
     * @return true if the current character is an escape character, false otherwise 
     */
    public static boolean isEscapeCharacter(Character character) {
        return provider.isEscapeCharacter(character);
    }
}
