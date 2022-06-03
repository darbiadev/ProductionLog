// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class EmployeeMixIn
{
    EmployeeMixIn(@JsonProperty("id") final int id, @JsonProperty("firstName") final String firstName, @JsonProperty("lastName") final String lastName) {
    }
}
