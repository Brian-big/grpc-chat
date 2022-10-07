package server;

import io.brianbig.chat.Chat;
import io.brianbig.chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.LinkedHashSet;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private static final LinkedHashSet<StreamObserver<Chat.ChatMessageFromServer>> observers = new LinkedHashSet<>();
    @Override
    public StreamObserver<Chat.ChatMessage> chat(StreamObserver<Chat.ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);
        return new StreamObserver<>() {
            @Override
            public void onNext(Chat.ChatMessage chatMessage) {
                //receiving the data from the client

                Chat.ChatMessageFromServer chatMessage_ = Chat.ChatMessageFromServer.newBuilder()
                        .setChatMessage(chatMessage)
                        .build();

                observers.forEach(
                        observer -> observer.onNext(chatMessage_)
                );
            }

            @Override
            public void onError(Throwable t) {
                observers.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }
}
