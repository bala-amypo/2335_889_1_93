@Service
public class InteractionServiceImpl implements InteractionService {

    private final MedicationRepository medRepo;
    private final InteractionRuleRepository ruleRepo;
    private final InteractionCheckResultRepository resultRepo;

    public InteractionServiceImpl(MedicationRepository m,
                                  InteractionRuleRepository r,
                                  InteractionCheckResultRepository res) {
        this.medRepo = m;
        this.ruleRepo = r;
        this.resultRepo = res;
    }

    public InteractionCheckResult checkInteractions(List<Long> ids) {

        List<Medication> meds = medRepo.findAllById(ids);
        if (meds.size() != ids.size())
            throw new ResourceNotFoundException("Medication not found");

        List<ActiveIngredient> ings = meds.stream()
                .flatMap(m -> m.getIngredients().stream())
                .toList();

        List<String> found = new ArrayList<>();

        for (int i=0;i<ings.size();i++) {
            for (int j=i+1;j<ings.size();j++) {
                ruleRepo.findRuleBetweenIngredients(
                        ings.get(i).getId(),
                        ings.get(j).getId()
                ).ifPresent(r -> found.add(
                        ings.get(i).getName()+"-"+ings.get(j).getName()+"("+r.getSeverity()+")"
                ));
            }
        }

        return resultRepo.save(
                new InteractionCheckResult(
                        meds.stream().map(Medication::getName).toList().toString(),
                        found.toString()
                )
        );
    }

    public InteractionCheckResult getResult(Long id) {
        return resultRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found"));
    }
}
