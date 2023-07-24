package com.tmmf.test.controller;

import com.tmmf.test.dto.ItemDto;
import com.tmmf.test.exception.ItemNotFoundException;
import com.tmmf.test.exception.ItemNotValidException;
import com.tmmf.test.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/service/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItem () {
        return ResponseEntity.ok(itemService.loadAllItem());
    }

    @PostMapping
    public ResponseEntity<ItemDto> postItem (@RequestBody ItemDto itemDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemDto));
        } catch (ItemNotValidException error) {
            return ResponseEntity.badRequest().build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<ItemDto> putItem (@RequestBody ItemDto itemDto) {
        try {
            return ResponseEntity.ok(itemService.updateItem(itemDto));
        } catch (ItemNotValidException error) {
            return ResponseEntity.badRequest().build();
        } catch (ItemNotFoundException error) {
            return ResponseEntity.notFound().build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<ItemDto> deleteItem (@PathParam("id") long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok().build();
        } catch (ItemNotValidException error) {
            return ResponseEntity.badRequest().build();
        } catch (ItemNotFoundException error) {
            return ResponseEntity.notFound().build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
