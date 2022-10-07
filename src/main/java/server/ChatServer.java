package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8888)
                .addService(new ChatService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
