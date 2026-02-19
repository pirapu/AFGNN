public class func {
public void main(String[] args){
        for(int i = 0; i < 3; i++){
            executorService.submit(new Task(foo, i));
        }
}
}
