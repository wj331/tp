package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INDEX_TOO_SMALL;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "2", new DeleteCommand(INDEX_SECOND_PERSON));
    }
    //Heuristics: Equivalence Partition: empty string/alphabet/ non integer/ integers
    //Heuristics: Boundary Value Analysis: -1, 0, 1 should be tested
    @Test
    public void parse_stringArg_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX);
    }
    @Test
    public void parse_negativeArg_throwsParseException() {
        assertParseFailure(parser, "-1", MESSAGE_INDEX_TOO_SMALL);
    }
    @Test
    public void parse_decimal_throwsParseException() {
        assertParseFailure(parser, "0.2", MESSAGE_INVALID_INDEX);
    }
    @Test
    public void parse_zero_throwsParseException() {
        assertParseFailure(parser, "0", MESSAGE_INDEX_TOO_SMALL);
    }
    @Test
    public void parse_emptyStr_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_one_success() {
        assertParseSuccess(parser, "1", new DeleteCommand(Index.fromOneBased(1)));
    }
}
