package com.example.demo.controllers;

import com.example.demo.models.InteractionRule;
import com.example.demo.services.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/rules")
@Tag(name = "Rules", description = "Interaction Rules API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RuleController {
    
    @Inject
    private RuleService ruleService;
    
    @POST
    @Path("/")
    @Operation(summary = "Add interaction rule", description = "Add a new drug interaction rule")
    public Response addRule(InteractionRule rule) {
        try {
            InteractionRule savedRule = ruleService.addRule(rule);
            return Response.status(Response.Status.CREATED).entity(savedRule).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/")
    @Operation(summary = "List all rules", description = "Get a list of all interaction rules")
    public Response getAllRules() {
        List<InteractionRule> rules = ruleService.getAllRules();
        return Response.ok(rules).build();
    }
}