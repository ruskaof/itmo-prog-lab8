package com.ruskaof.client.connection;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataCantBeSentException;

public interface ConnectionManager {
    CommandResultDto sendCommand(CommandFromClientDto commandFromClientDto) throws DataCantBeSentException;
}
