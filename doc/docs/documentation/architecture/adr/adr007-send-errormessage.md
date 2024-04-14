# ADR-007 send errormessage

## Status

<adr-status status='accepted'/>

## Context

What is the issue that we're seeing that is motivating this decision or change?
Currently, when an error occurs in the backend, the frontend and the user don't get any information about the error.
They just get notified that an error occurred.

## Decision

Every time an error occurs in the backend, we send the error message as a string to the frontend with an appropriate HTTP status code.

## Consequences

We can provide more detailed information to the user, so the user can act accordingly to the error.