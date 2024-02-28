package kr.ezen.planet.common;

public class ApiResponse<T> {
private boolean success;   
private String message;
private T data;

public ApiResponse() {
}


   
// 성공 여부와 메시지만 포함하는 생성자
    
   
public ApiResponse(boolean success, String message) {
        
       
this.success = success;
        
       
this.message = message;
    }



   
// 성공 여부, 메시지, 데이터를 모두 포함하는 생성자
    
   
public ApiResponse(boolean success, String message, T data) {
        
       
this.success = success;
        
       
this.message = message;
        
       
this.data = data;
    }


   
// Getter와 Setter 메소드
    
   
public boolean isSuccess() {
        
       
return success;
    }

    



   
public void setSuccess(boolean success) {
        this.success = success;
    }

    

public String getMessage() {
        
       
return message;
    }


public void setMessage(String message) {
        
       
this.message = message;
    }

    

public T getData() {
return data;}

    
 
   
public void setData(T data) {
this.data = data;}


    
}

    
