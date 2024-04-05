package com.example.catalog.integration.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.catalog.exception.AccessDeniedException;
import com.example.catalog.exception.BadRequestException;
import com.example.catalog.exception.EntityNotFoundException;
import com.example.catalog.exception.UnauthenticationException;
import com.example.catalog.exception.ValidationException;
import com.example.catalog.shared.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class ExceptionHandlerIntegrationTest extends AbstractIntegrationTest {

  @Test
  public void givenAccessDenied_whenGetSpecificException_thenForbidden() throws Exception {
    String exceptionParam = "accessDenied";

    mockMvc.perform(get("/test/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden())
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof AccessDeniedException));
  }

  @Test
  public void givenBadRequest_whenGetSpecificException_thenBadRequest() throws Exception {
    String exceptionParam = "badRequest";

    mockMvc.perform(get("/test/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof BadRequestException))
        .andExpect(result ->
            assertEquals("bad arguments", result.getResolvedException().getMessage()));
  }

  @Test
  public void givenEntityNotFound_whenGetSpecificException_thenNotFound() throws Exception {
    String exceptionParam = "entityNotFound";

    mockMvc.perform(get("/test/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
        .andExpect(result ->
            assertEquals("Entity not found.", result.getResolvedException().getMessage()));
  }

  @Test
  public void givenUnauthentication_whenGetSpecificException_thenUnauthorized() throws Exception {
    String exceptionParam = "unauthentication";

    mockMvc.perform(get("/test/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof UnauthenticationException));
  }

  @Test
  public void givenValidation_whenGetSpecificException_thenBadRequest() throws Exception {
    String exceptionParam = "validation";

    mockMvc.perform(get("/test/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(result ->
            assertTrue(result.getResolvedException() instanceof ValidationException))
        .andExpect(result ->
            assertEquals("You didn't pass validation.",
                    result.getResolvedException().getMessage()));
  }

  @Test
  public void givenOther_whenGetSpecificException_thenInternalServerError() throws Exception {
    String exceptionParam = "test";

    mockMvc.perform(get("/test/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(result -> assertNotNull(result.getResolvedException()))
        .andExpect(result ->
                assertEquals("internal error", result.getResolvedException().getMessage()));
  }
}
