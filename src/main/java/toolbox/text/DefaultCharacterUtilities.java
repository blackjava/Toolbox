package toolbox.text;

/**
 * Default implementation of CharacterUtilitiesProvider.
 */
public class DefaultCharacterUtilities implements CharacterUtilitiesProvider {
    public boolean isOpeningCurlyBracket(Character character) {
        return character.equals('{');
    }

    public boolean isOpeningParenthesis(Character character) {
        return character.equals('(');
    }

    public boolean isClosingCurlyBracket(Character character) {
        return character.equals('}');
    }

    public boolean isClosingParenthesis(Character character) {
        return character.equals(')');
    }

    public boolean isOpeningSquareBracket(Character character) {
        return character.equals('[');
    }

    public boolean isClosingSquareBracket(Character character) {
        return character.equals(']');
    }

    public boolean isQuote(Character character) {
        return character.equals('\'') || character.equals('"');
    }
    
    public boolean isSymbol(Character character) {
        return (!Character.isLetterOrDigit(character) && !Character.isWhitespace(character));
    }
}
