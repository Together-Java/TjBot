package org.togetherjava.discordbot.db.repository;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.togetherjava.discordbot.commands.CommandContext;

public abstract class SimpleRepository<T> implements Repository<T> {

    protected DSLContext dslContext;

    public SimpleRepository(CommandContext context) {
        dslContext = DSL.using(context.getConfig().getDburl());
    }

}
