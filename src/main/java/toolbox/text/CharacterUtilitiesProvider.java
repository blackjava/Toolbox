package toolbox.text;

/**
 * Utility functionality for working with characters.
 */
public interface CharacterUtilitiesProvider {
    boolean isOpeningCurlyBracket(Character character);
    boolean isOpeningParenthesis(Character character);
    boolean isClosingCurlyBracket(Character character);
    boolean isClosingParenthesis(Character character);
    boolean isOpeningSquareBracket(Character character);
    boolean isClosingSquareBracket(Character character);
    boolean isQuote(Character character);
    boolean isSymbol(Character character);
}
