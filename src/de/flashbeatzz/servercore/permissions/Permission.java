package de.flashbeatzz.servercore.permissions;

public enum Permission {

    API("api.*", null, true);

    private String perm;
    private Permission parentPerm;
    private Boolean isParentPerm;

    /**
     *
     * @param perm The permission as a String
     * @param parentPerm The parent-permission (if available)
     * @param parent_Perm If the permission is a parent-permission
     *
     */

    Permission(String perm, Permission parentPerm, Boolean parent_Perm) {
        this.perm = perm;
        this.parentPerm = parentPerm;
        this.isParentPerm = parent_Perm;
    }

    public String getPermissionToString() {
        return this.perm;
    }

    public Permission getParentPerm() {
        return this.parentPerm;
    }

    public Boolean isParentPerm() {
        return this.isParentPerm;
    }

    public static void loadPermissions() {
        PermissionGroup.loadPermissionGroups();
    }

}
