package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.client_cache_key.ClientCacheKey;
import org.finos.waltz.service.client_cache_key.ClientCacheKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/client-cache-key")

public class ClientCacheKeyEndpointController {
    private static final String BASE_URL = mkPath("api", "client-cache-key");

    private final ClientCacheKeyService clientCacheKeyService;


    @Autowired
    public ClientCacheKeyEndpointController(ClientCacheKeyService clientCacheKeyService) {
        this.clientCacheKeyService = clientCacheKeyService;
    }


    @GetMapping("/all")
    List<ClientCacheKey> findAll() {
        return clientCacheKeyService.findAll();
    }

    @GetMapping("/key/{key}")
    ClientCacheKey getByKey(@PathVariable String key) {
        return clientCacheKeyService.getByKey(key);
    }

    @PostMapping("/update/{key}")
    ClientCacheKey createOrUpdate(@PathVariable String key) {
        return clientCacheKeyService.createOrUpdate(key);
    }
}
