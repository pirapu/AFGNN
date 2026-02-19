public String getCarInsuranceNameFromPersonUsingFlatMap(Person person) {
    return ofNullable(person)
            .flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getName)
            .orElse("Unknown");
}
