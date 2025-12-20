@Service
public class CatalogServiceImpl implements CatalogService {

    private final ActiveIngredientRepository ingredientRepo;
    private final MedicationRepository medicationRepo;

    public CatalogServiceImpl(ActiveIngredientRepository i, MedicationRepository m) {
        this.ingredientRepo = i;
        this.medicationRepo = m;
    }

    public ActiveIngredient addIngredient(ActiveIngredient ingredient) {
        if (ingredientRepo.existsByName(ingredient.getName()))
            throw new IllegalArgumentException("Ingredient exists");
        return ingredientRepo.save(ingredient);
    }

    public Medication addMedication(String name, List<Long> ids) {
        Medication med = new Medication(name);
        ids.forEach(id ->
            med.getIngredients().add(
                ingredientRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"))
            )
        );
        return medicationRepo.save(med);
    }

    public List<Medication> getAllMedications() {
        return medicationRepo.findAll();
    }
}
