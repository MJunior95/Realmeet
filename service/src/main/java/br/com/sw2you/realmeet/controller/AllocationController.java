package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import br.com.sw2you.realmeet.api.facade.AllocationsApi;
import br.com.sw2you.realmeet.api.model.*;
import br.com.sw2you.realmeet.service.AllocationService;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllocationController implements AllocationsApi {

    private final Executor controllersExecutor;
    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService, Executor controllersExecutor) {
        this.allocationService = allocationService;
        this.controllersExecutor = controllersExecutor;
    }


  @Override
    public CompletableFuture<ResponseEntity<AllocationDTO>> createAllocation(CreateAllocationDTO createAllocationDTO){
      return supplyAsync(() -> allocationService.createAllocation(createAllocationDTO), controllersExecutor).thenApply(ResponseEntityUtils::created);
  }

}
