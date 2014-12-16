package toolbox.text;

/**
 * Utility functionality for working with characters.
 */
public class CharacterUtilities {
    private static CharacterUtilitiesProvider provider = new DefaultCharacterUtilities();
    
    public static boolean isOpeningCurlyBracket(Character character) {
        return provider.isOpeningCurlyBracket(character);
    }
    
    public static boolean isOpeningParenthesis(Character character) {
        return provider.isOpeningParenthesis(character);
    }
    
    public static boolean isClosingCurlyBracket(Character character) {
        return provider.isClosingCurlyBracket(character);
    }
    
    public static boolean isClosingParenthesis(Character character) {
        return provider.isClosingParenthesis(character);
    }
    
    public static boolean isOpeningSquareBracket(Character character) {
        return provider.isOpeningSquareBracket(character);
    }
    
    public static boolean isClosingSquareBracket(Character character) {
        return provider.isClosingSquareBracket(character);
    }
    
    public static boolean isQuote(Character character) {
        return provider.isQuote(character);
    }
    
    public static boolean isSymbol(Character character) {
        return provider.isSymbol(character);
    }

    public static boolean isEscapeCharacter(Character character) {
        return provider.isEscapeCharacter(character);
    }
}
