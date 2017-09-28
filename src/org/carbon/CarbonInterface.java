package org.carbon;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Ethan
 */
public abstract class CarbonInterface {
    private CarbonScope scope;

    // Not sure if these should have a default implementation or not, but a reasonable
    // default exists, so eh there you go
    public Collection<String> getAllMemberNames() { return Collections.EMPTY_SET; }
    public Optional<CarbonInterface> getMember(String name) { return Optional.empty(); }

    public Collection<CarbonInterface> getSuperInterfaces() { return Collections.EMPTY_SET; }
}
