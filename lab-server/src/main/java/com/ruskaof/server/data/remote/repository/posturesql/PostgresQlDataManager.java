package com.ruskaof.server.data.remote.repository.posturesql;

import com.ruskaof.common.util.CollectionManager;

public class PostgresQlDataManager {
    private final CollectionManager collectionManager;
    private final Database database;

    public PostgresQlDataManager(CollectionManager collectionManager, Database database) {
        this.collectionManager = collectionManager;
        this.database = database;
    }


}