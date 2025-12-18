@RestController
@RequestMapping("/catalog")
@Tag(name = "Catalog", description = "Medication Catalog API")
@CrossOrigin(origins = "http://localhost:3000")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping("/ingredient")
    @Operation(summary = "Add ingredient")
    public ResponseEntity<?> addIngredient(@Valid @RequestBody ActiveIngredient ingredient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catalogService.addIngredient(ingredient));
    }

    @PostMapping("/medication")
    @Operation(summary = "Add medication")
    public ResponseEntity<?> addMedication(@Valid @RequestBody Medication medication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catalogService.addMedication(medication));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<ActiveIngredient>> getAllIngredients() {
        return ResponseEntity.ok(catalogService.getAllIngredients());
    }

    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> getAllMedications() {
        return ResponseEntity.ok(catalogService.getAllMedications());
    }
}
