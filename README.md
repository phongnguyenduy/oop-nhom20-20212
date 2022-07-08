# Bài tập lớn Lập trình hướng đối tượng
- Đề tài: **Phần mềm quản lý y tế** 
- Nhóm 20
- Kì 20212
- Lớp 132623

## Thành viên
1. Nguyễn Duy Phong
2. Nguyễn Thị Hồng Vân
3. Nguyễn Tất Minh
4. Nguyễn Bá Tuấn
5. Vũ Nam Hoàng

## Chạy app trên máy tính của mình
- Clone repository vào IDE của mình, ưu tiên dùng **IntelliJ IDEA**
- Cài Postgres SQL
> Nếu được hỏi mật khẩu admin (**username postgres**), nhập mật khẩu **121102**, vì file kết nối đang dùng mật khẩu đó.
> Ngoài ra chấp nhận các thiết lập mặc định khác
- Cài Postgres SQL xong thì mở pgAdmin, mở file **database_oop.sql**, rồi chạy để tạo CSDL
- Khi chạy, thiết lập **tham số máy ảo (VM arguments)** như sau:
- **Nếu có thay đổi file nào thì COMMIT và PUSH**
- (chi tiết về cách làm việc với Git và GitHub các bạn vui lòng tìm hiểu trên mạng)
```
--module-path [YOUR_PATH_HERE] --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.web,javafx.graphics
//[YOUR_PATH_HERE] là xâu ký tự bao trong ngoặc kép chỉ dẫn đường dẫn đến thư mục lib của JavaFX
```

## Cấu trúc thư mục
- Thư mục **projectLibraries** chứa các thư viện cần để app vận hành
