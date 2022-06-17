package com.ruskaof.client.connection;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;

import java.io.IOException;

public interface CommandSender {
    CommandResultDto sendCommand(CommandFromClientDto commandFromClientDto) throws IOException;
}
