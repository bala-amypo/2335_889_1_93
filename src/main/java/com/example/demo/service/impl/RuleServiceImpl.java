@Service
public class RuleServiceImpl implements RuleService {

    private final InteractionRuleRepository ruleRepo;
    private final ActiveIngredientRepository ingredientRepo;

    public RuleServiceImpl(InteractionRuleRepository r, ActiveIngredientRepository i) {
        this.ruleRepo = r;
        this.ingredientRepo = i;
    }

    public InteractionRule addRule(Long a, Long b, String severity, String desc) {

        if (!List.of("MINOR","MODERATE","MAJOR").contains(severity))
            throw new IllegalArgumentException("Invalid severity");

        ruleRepo.findRuleBetweenIngredients(a, b)
                .ifPresent(r -> { throw new IllegalArgumentException("Rule exists"); });

        return ruleRepo.save(new InteractionRule(
                ingredientRepo.findById(a).orElseThrow(),
                ingredientRepo.findById(b).orElseThrow(),
                severity,
                desc
        ));
    }

    public List<InteractionRule> getAllRules() {
        return ruleRepo.findAll();
    }
}
