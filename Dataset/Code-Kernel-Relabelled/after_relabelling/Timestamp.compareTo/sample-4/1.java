public class func{
public void getTable(String[] names){
        if ((labelTimestamp != null) && (table instanceof FemLocalTable)) {
            FemAnnotatedElement annotated = (FemAnnotatedElement) table;
            Timestamp objectCreateTimestamp =
                Timestamp.valueOf(annotated.getCreationTimestamp());
            if (objectCreateTimestamp.compareTo(labelTimestamp) > 0) {
                throw FarragoResource.instance()
                .ValidatorAccessObjectNonVisibleToLabel.ex(
                    getRepos().getLocalizedObjectName(table));
            }
        }
        addDependency(table, action);
        if (table.getVisibility() == null) {
            throw new FarragoUnvalidatedDependencyException();
        }
        RelDataType rowType = createTableRowType(table);
        if (table instanceof FemLocalTable) {
            int nColumnsTotal = rowType.getFieldCount();
            int nColumnsActual = nColumnsTotal;
            DependencySupplier supplier =
                getRepos().getCorePackage().getDependencySupplier();
            for (CwmDependency dep : supplier.getSupplierDependency(table)) {
                if (dep.getNamespace() instanceof FemRecoveryReference) {
                    FemRecoveryReference recoveryRef =
                        (FemRecoveryReference) dep.getNamespace();
                    if (recoveryRef.getRecoveryType()
                        == RecoveryTypeEnum.ALTER_TABLE_ADD_COLUMN)
                    {
                        --nColumnsActual;
                        break;
                    }
                }
            }
            if (labelTimestamp != null) {
                while (nColumnsActual > 1) {
                    FemStoredColumn column =
                        (FemStoredColumn) table.getFeature().get(
                            nColumnsActual - 1);
                    Timestamp columnCreateTimestamp =
                        Timestamp.valueOf(column.getCreationTimestamp());
                    if (columnCreateTimestamp.compareTo(labelTimestamp) > 0) {
                        --nColumnsActual;
                    } else {
                        break;
                    }
                }
            }
            if (nColumnsActual < nColumnsTotal) {
                rowType =
                    getFarragoTypeFactory().createStructType(
                        RelOptUtil.getFieldTypeList(rowType).subList(
                            0,
                            nColumnsActual),
                        RelOptUtil.getFieldNameList(rowType).subList(
                            0,
                            nColumnsActual));
            }
        }
            FarragoCatalogUtil.getTableAllowedAccess(table);
}
}
