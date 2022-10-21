public class Guest {
    private String name;
    private String job;
    private String career;
    private int age = 0;

    private String interestingFact1, interestingFact2;

    public Guest() {
    }

    public Guest(String guestName,
            String guestJob,
            String guestCareer,
            int guestAge,
            String guestInterestingFact1,
            String guestInterestingFact2) {
        name = guestName;
        job = guestJob;
        career = guestCareer;
        age = guestAge;
        interestingFact1 = guestInterestingFact1;
        interestingFact2 = guestInterestingFact2;
    }

    public String getString() {
        return String.format("%s, %s", interestingFact1, interestingFact2);
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setJob(String newJob) {
        job = newJob;
    }

    public void setCareer(String newCareer) {
        career = newCareer;
    }

    public void setAge(int newAge) {
        age = newAge;
    }

    public void setFact1(String newFact) {
        interestingFact1 = newFact;
    }

    public void setFact2(String newFact) {
        interestingFact2 = newFact;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getCareer() {
        return career;
    }

    public int getAge() {
        return age;
    }

    public String getFact1() {
        return interestingFact1;
    }

    public String getFact2() {
        return interestingFact2;
    }
}