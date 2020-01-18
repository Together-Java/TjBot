package org.togetherjava.discordbot.commands.commands;

import de.ialistannen.commandprocrastination.autodiscovery.ActiveCommand;
import de.ialistannen.commandprocrastination.command.tree.CommandNode;
import de.ialistannen.commandprocrastination.parsing.ParseException;
import org.togetherjava.discordbot.commands.CommandContext;
import org.togetherjava.discordbot.db.autogen.tables.pojos.Test;
import org.togetherjava.discordbot.db.repositories.ExampleRepository;

import static de.ialistannen.commandprocrastination.parsing.defaults.StringParsers.greedyPhrase;

@ActiveCommand(name = "example", parentClass = BasePrefixCommand.class)
@SuppressWarnings("unused")
public class ExampleCommandDB extends CommandNode<CommandContext> {

    private ExampleRepository repository;

    @SuppressWarnings("unused")
    public ExampleCommandDB(CommandContext context) {
        super("example");
        repository = new ExampleRepository(context);
        setCommand(this::execute);
    }

    private void execute(CommandContext context) throws ParseException {
        repository.add(new Test(null, context.getRequestContext().getUser().getName(), context.shift(greedyPhrase())));
        for(Test value: repository.getAll()){
            context.getRequestContext().getChannel().sendMessage(value.getText()).queue();
        }

        for(Test value: repository.getAll()){
            value.setText(value.getText() + "UPDATE TEST");
            repository.update(value);
        }
    }
}
