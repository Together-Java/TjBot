package org.togetherjava.discordbot.db.repositories;

import org.togetherjava.discordbot.commands.CommandContext;
import org.togetherjava.discordbot.db.autogen.tables.daos.TestDao;
import org.togetherjava.discordbot.db.autogen.tables.pojos.Test;
import org.togetherjava.discordbot.db.repository.SimpleRepository;
import java.util.List;

/**
 * can use the dao or the DSLContext to write queries, the dao having most things pre written
 */
public class ExampleRepository extends SimpleRepository<Test> {

    private TestDao dao;

    public ExampleRepository(CommandContext context){
        super(context);
        dao = new TestDao(dslContext.configuration());
    }

    @Override
    public void add(Test item) {
        dao.insert(item);
    }

    @Override
    public void update(Test item) {
        dao.update(item);
    }

    @Override
    public void remove(Test item) {
        dao.delete(item);
    }

    @Override
    public List<Test> getAll() {
        return dao.findAll();
    }

}
