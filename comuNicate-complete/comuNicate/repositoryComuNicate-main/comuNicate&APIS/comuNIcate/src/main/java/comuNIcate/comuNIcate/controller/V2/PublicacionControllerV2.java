package comuNIcate.comuNIcate.controller.V2;

import comuNIcate.comuNIcate.assemblers.PublicacionModelAssembler;
import comuNIcate.comuNIcate.model.Publicacion;
import comuNIcate.comuNIcate.services.PublicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v2/publicaciones")
@Tag(name = "Publicaciones V2", description = "Operaciones relacionadas con publicacion")
public class PublicacionControllerV2 {

    @Autowired
    private PublicacionService pubServ;

    @Autowired
    private PublicacionModelAssembler assembler;

    //Obtiene todas las publicaciones
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener publicaciones",
        description = "Obtiene una lista de todas las publicaciones"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicaciones obtenidas correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay publicaciones a mostrar")
    })
    public CollectionModel<EntityModel<Publicacion>> getAllPublicaciones(){
        List<EntityModel<Publicacion>> publicaciones = pubServ.findAllPublicaciones().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(publicaciones,
            linkTo(methodOn(PublicacionControllerV2.class).getAllPublicaciones()).withSelfRel());
    }
    //Obtiene una publicacion por ID
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
     @Operation(
        summary = "Obtener publicacion por ID",
        description = "Obtiene una publicacion por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicacion obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Publicacion no encontrada")
    })
    public ResponseEntity<EntityModel<Publicacion>> getPublicacionById(
        @Parameter(description = "ID de publicacion", required = true)
        @PathVariable Long id
    ){
        try {
            Publicacion publicacion = pubServ.findPublicacionById(id);
            return ResponseEntity.ok(assembler.toModel(publicacion));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //Obtiene publicaciones de un usuario
    @GetMapping(value = "/IDUsuario/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener publicaciones por usuario",
        description = "Obtiene una lista de publicaciones por ID de usuario"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicaciones obtenidas correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay publicaciones para mostrar")
    })
    public CollectionModel<EntityModel<Publicacion>> getPublicacionesByUsuario(
        @Parameter(description = "ID de usuario", required = true)
        @PathVariable Long idUsuario
    ){
        List<EntityModel<Publicacion>> publicaciones = pubServ.findPublicacionesByIdUsuario(idUsuario).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(publicaciones,
            linkTo(methodOn(PublicacionControllerV2.class).getPublicacionesByUsuario(idUsuario)).withSelfRel());
    }
    //Obtiene detalle de todas las publicacion
    @GetMapping(value = "/resumen", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener resumen publicaciones",
        description = "Obtiene una lista de publicaciones y sus resumenes"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumen de publicaciones obtenidos correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay resumenes para mostrar")
    })
    public ResponseEntity<List<Map<String, Object>>> getResumenPublicaciones() {
    List<Map<String, Object>> resumenes = pubServ.findDetallesPublicaciones();
    if (resumenes.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(resumenes);
    }       
    //Agrega una publicacion
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Agregar publicacion",
        description = "Agrega una publicacion en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicacion guardada correctamente",
            content = @Content(
                schema = @Schema(implementation = Publicacion.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar publicacion")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Publicacion a agregar",
        required = true
    )
    public ResponseEntity<EntityModel<Publicacion>> createPublicacion(@RequestBody Publicacion publicacion){
        Publicacion nueva = pubServ.savePublicacion(publicacion);
        return ResponseEntity
        .created(linkTo(methodOn(PublicacionControllerV2.class).getPublicacionById(nueva.getIdPublicacion())).toUri())
        .body(assembler.toModel(nueva));
    }
    //Modifica una publicacion
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Modificar publicacion",
        description = "Modifica completamente una publicacion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicacion modificada correctamente",
            content = @Content(
                schema = @Schema(implementation = Publicacion.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Publicacion no encontrada"),
        @ApiResponse(responseCode = "400", description = "Error al modificar publicacion")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Comuna a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<Publicacion>> updatePublicacion(
        @Parameter(description = "ID de publicacion", required = true)
        @PathVariable Long id, @RequestBody Publicacion publicacion
    ){
        try {
            Publicacion actualizada = pubServ.updatePublicacion(id, publicacion);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //Modifica parcialmente una publicacion
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Modificar publicacion",
        description = "Modifica parcialmente una publicacion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Publicacion modificada correctamente",
            content = @Content(
                schema = @Schema(implementation = Publicacion.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Publicacion no encontrada"),
        @ApiResponse(responseCode = "400", description = "Error al modificar publicacion")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Publicacion a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<Publicacion>> patchPublicacion(
        @Parameter(description = "ID de publicacion", required = true)    
        @PathVariable Long id, @RequestBody Publicacion publicacion
    ){
        try {
            Publicacion actualizada = pubServ.partialUpdatePublicacion(id, publicacion);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //Elimina una publicacion
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Eliminar publicacion",
        description = "Elimina una publicacion por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Publicacion eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Publicacion no encontrada")
    })
    public ResponseEntity<Void> deletePublicacion(
        @Parameter(description = "ID de publicacion", required = true)
        @PathVariable Long id) {
        try {
            pubServ.deletePublicacionById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
