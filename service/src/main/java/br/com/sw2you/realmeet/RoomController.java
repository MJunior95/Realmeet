package br.com.sw2you.realmeet;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.RoomsApi;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;

import br.com.sw2you.realmeet.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController implements RoomsApi {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(Long id) {
        return supplyAsync(() -> ResponseEntity.ok(roomService.getRoom(id)));
    }

}
