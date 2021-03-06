package ServiceOne;

public class Ship {
    private int timeOfArrival;
    private String name;
    private Cargo cargo;
    private int weight;
    private int unloadingTime;
    private int workingUnloadingTime;
    private int waitTime = 0;

    public void writeWorkingTime()
    {
        workingUnloadingTime = unloadingTime;
    }
    synchronized public void decWorkUploadingTime()
    {
        workingUnloadingTime--;
    }
    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    synchronized public void incWaitTime()
    {
        waitTime++;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setUnloadingTime(int time)
    {
        this.unloadingTime = time;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setDelayTime(int delayTime) {
        this.timeOfArrival += delayTime;
    }

    public void setDelayUploadingTime(int delayUploadingTime) {
        this.unloadingTime += delayUploadingTime;
    }

    public int getRealTimeOfArrival() {
        return timeOfArrival;
    }

    public int getTimeOfArrival() {
        return timeOfArrival;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public int getUnloadingTime() {
        return unloadingTime;
    }

    public int getWorkingUnloadingTime() {
        return workingUnloadingTime;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        int days = timeOfArrival / 1440;
        int hours = timeOfArrival / 60 % 24;
        int minutes = timeOfArrival % 60;
        string.append("\n\nTime of arrival: " + days + " day " + hours + " hours " + minutes + " minutes\n");
        string.append("Name of Ship: " + name + "\n");
        string.append("Type of cargo: " + cargo.getType() + "\n");
        if (cargo.getType() == Cargo.TypeOfCargo.CONTAINER) {
            string.append("Amounts of containers: ");
        } else {
            string.append("Weight is: ");
        }
        string.append(weight + "\n");
        days = unloadingTime / 1440;
        hours = unloadingTime / 60 % 24;
        minutes = unloadingTime % 60;
        string.append("Time of unloading: " + days + " day " + hours + " hours " + minutes + " minutes");
        return string.toString();
    }

    public Ship(String name, Cargo cargo, int timeOfArrival, int unloadingTime, int weight) {
        this.cargo = cargo;
        this.name = name;
        this.timeOfArrival = timeOfArrival;
        this.unloadingTime = unloadingTime;
        this.weight = weight;
    }
}
