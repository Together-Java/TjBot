# Contributing guidelines
## Got an idea for a helpful feature?
If you have an idea for a new feature please feel free to create a new issue.
Mark the issue as `enhancement`.

In your issue please try to include:
 - What does the new feature do?
 - How does this feature benefit the server?
 - An example of usage or something similar.
 
Even if you cannot provide all points still create an issue.
Those points just help better describing your feature, meaning it is better to discuss for other contributors.

### Examples
[Issue #1](https://github.com/Together-Java/TjBot/issues/1)
[Issue #2](https://github.com/Together-Java/TjBot/issues/2)
[Issue #3](https://github.com/Together-Java/TjBot/issues/3)

## Found a bug?
If you found a bug please create an issue.
Mark the issue as `bug`.

Try to include as much information as possible in order to recreate the bug.
This might include:

 - Expected output
 - Actual output
 - An idea how the bug might have been caused
 - A minimal working example that recreates the bug

Again those points are not required but help future contributors to easier track the bug down.

## You want to write code?

### New commands 
1. Make a new class in the `org.togetherjava.command.commands` package.
  All classes in that package are automatically registered at runtime.
2. Make sure the class *has a constructor that takes no arguments* or takes a single `Toml` instance (the bot config).
3. Implement the mandated method:
   ```java
   public LiteralCommandNode<CommandSource> getCommand(CommandDispatcher<CommandSource> dispatcher) {
   ```
4. Build your command in there. Here is the echo command, which might serve as an example:
   ```java
   @Override
   public LiteralCommandNode<CommandSource> getCommand(CommandDispatcher<CommandSource> dispatcher) {
     return CommandGenericHelper.literal("echo")
         .then(
             CommandGenericHelper.argument("message", StringArgumentType.greedyString())
                 .executes(context -> {
                   CommandSource source = context.getSource();
                   String argument = context.getArgument("message", String.class);
 
                   source.getMessageSender()
                       .sendMessage(SimpleMessage.information(argument), source.getChannel());
                   return 0;
                 })
         )
         .build();
   }
   ```
5. For more information on adding commands, refer to the documentation for Brigardier or ask :)

## Structuring a Pull Request

### Code
Please only submit one Feature per PR. 
It makes it easier to review and log which improvemant came from where.
Do create a branch per feature so that you do not accidentally create a PR for multiple features.

### Description
Within the Description of the PR please include the feature you implemented.
Best practice would be to link the corresponding issue.
This makes it easier for someone reviewing your code to understand your intent and properly check if you implemented it according to the initial requirements.

## Notes
Improvements to the Markdown files in this repo are also always welcome.
