# KỊCH BẢN TESTING CHO TALENTBRIDGE

## I. BACKEND TESTING (60 Test Cases)

### 1. Authentication & Authorization (12 cases)
1. **AUTH_01**: Đăng ký user mới với thông tin hợp lệ
2. **AUTH_02**: Đăng ký user với email đã tồn tại (expect fail)
3. **AUTH_03**: Đăng nhập với thông tin đúng
4. **AUTH_04**: Đăng nhập với mật khẩu sai (expect fail)
5. **AUTH_05**: Đăng nhập với email không tồn tại (expect fail)
6. **AUTH_06**: Refresh token hợp lệ
7. **AUTH_07**: Refresh token đã hết hạn (expect fail)
8. **AUTH_08**: Logout và invalidate token
9. **AUTH_09**: Truy cập API với JWT token hợp lệ
10. **AUTH_10**: Truy cập API với JWT token không hợp lệ (expect 401)
11. **AUTH_11**: Truy cập API không có quyền (expect 403)
12. **AUTH_12**: Kiểm tra session management

### 2. User Management (10 cases)
13. **USER_01**: Lấy danh sách users với phân trang
14. **USER_02**: Tìm kiếm user theo email
15. **USER_03**: Lấy thông tin user theo ID
16. **USER_04**: Cập nhật thông tin profile
17. **USER_05**: Cập nhật mật khẩu với mật khẩu cũ đúng
18. **USER_06**: Cập nhật mật khẩu với mật khẩu cũ sai (expect fail)
19. **USER_07**: Upload avatar thành công
20. **USER_08**: Upload avatar với file quá lớn (expect fail)
21. **USER_09**: Xóa user (soft delete)
22. **USER_10**: Khôi phục user đã xóa

### 3. Company Management (10 cases)
23. **COMPANY_01**: Tạo công ty mới với đầy đủ thông tin
24. **COMPANY_02**: Tạo công ty với tên trùng lặp (expect fail)
25. **COMPANY_03**: Lấy danh sách công ty với filter
26. **COMPANY_04**: Lấy chi tiết công ty theo ID
27. **COMPANY_05**: Cập nhật thông tin công ty
28. **COMPANY_06**: Upload logo công ty
29. **COMPANY_07**: Thêm recruiter vào công ty
30. **COMPANY_08**: Xóa recruiter khỏi công ty
31. **COMPANY_09**: Xóa công ty có job đang tuyển (expect fail)
32. **COMPANY_10**: Lấy danh sách công ty kèm số lượng job

### 4. Job Management (10 cases)
33. **JOB_01**: Tạo job mới với thông tin hợp lệ
34. **JOB_02**: Tạo job với salary min > max (expect fail)
35. **JOB_03**: Lấy danh sách job với filter theo level
36. **JOB_04**: Lấy danh sách job theo công ty
37. **JOB_05**: Cập nhật thông tin job
38. **JOB_06**: Đóng/mở job tuyển dụng
39. **JOB_07**: Xóa job không có CV ứng tuyển
40. **JOB_08**: Xóa job có CV ứng tuyển (expect fail)
41. **JOB_09**: Tìm kiếm job theo skills
42. **JOB_10**: Lấy job với phân trang và sorting

### 5. Resume Management (8 cases)
43. **RESUME_01**: Upload CV PDF hợp lệ
44. **RESUME_02**: Upload CV với định dạng không phải PDF (expect fail)
45. **RESUME_03**: Ứng tuyển job với CV
46. **RESUME_04**: Ứng tuyển cùng job 2 lần (expect fail)
47. **RESUME_05**: Cập nhật trạng thái CV (PENDING -> REVIEWING)
48. **RESUME_06**: Lấy danh sách CV theo job
49. **RESUME_07**: Lấy danh sách CV của user
50. **RESUME_08**: Xóa CV ứng tuyển

### 6. Role & Permission (6 cases)
51. **ROLE_01**: Tạo role mới với permissions
52. **ROLE_02**: Cập nhật permissions cho role
53. **ROLE_03**: Gán role cho user
54. **ROLE_04**: Kiểm tra user permissions
55. **ROLE_05**: Xóa role không được gán cho user nào
56. **ROLE_06**: Xóa role đang được sử dụng (expect fail)

### 7. Skill Management (4 cases)
57. **SKILL_01**: Tạo skill mới
58. **SKILL_02**: Lấy danh sách skills
59. **SKILL_03**: Cập nhật skill
60. **SKILL_04**: Xóa skill không được dùng

## II. FRONTEND TESTING (40+ Test Cases)

### 8. Authentication UI (8 cases)
61. **FE_AUTH_01**: Hiển thị form đăng nhập
62. **FE_AUTH_02**: Validation form đăng nhập (email format, required fields)
63. **FE_AUTH_03**: Hiển thị form đăng ký
64. **FE_AUTH_04**: Validation form đăng ký (password match, email format)
65. **FE_AUTH_05**: Chuyển hướng sau đăng nhập thành công
66. **FE_AUTH_06**: Hiển thị error message khi đăng nhập thất bại
67. **FE_AUTH_07**: Remember me functionality
68. **FE_AUTH_08**: Logout và clear local storage

### 9. Navigation & Routing (6 cases)
69. **FE_NAV_01**: Navigation menu hiển thị đúng theo role
70. **FE_NAV_02**: Protected routes redirect khi chưa login
71. **FE_NAV_03**: Admin routes chỉ accessible cho admin
72. **FE_NAV_04**: Recruiter routes chỉ accessible cho recruiter
73. **FE_NAV_05**: Breadcrumb navigation hoạt động
74. **FE_NAV_06**: 404 page hiển thị cho invalid routes

### 10. Job Listing Page (8 cases)
75. **FE_JOB_01**: Hiển thị danh sách jobs
76. **FE_JOB_02**: Filter jobs theo level (INTERN, FRESHER, JUNIOR, etc.)
77. **FE_JOB_03**: Search jobs theo tên
78. **FE_JOB_04**: Search jobs theo địa điểm
79. **FE_JOB_05**: Pagination hoạt động đúng
80. **FE_JOB_06**: Job card hiển thị đầy đủ thông tin
81. **FE_JOB_07**: Click job card mở job details
82. **FE_JOB_08**: Responsive layout trên mobile/tablet

### 11. Job Details & Application (6 cases)
83. **FE_APPLY_01**: Hiển thị chi tiết job
84. **FE_APPLY_02**: Nút "Ứng tuyển" chỉ hiện khi đã login
85. **FE_APPLY_03**: Form upload CV validation
86. **FE_APPLY_04**: Success message sau khi ứng tuyển
87. **FE_APPLY_05**: Không cho phép ứng tuyển lại job đã apply
88. **FE_APPLY_06**: Hiển thị công ty info trong job details

### 12. Admin Dashboard (6 cases)
89. **FE_ADMIN_01**: Dashboard hiển thị statistics cards
90. **FE_ADMIN_02**: Sidebar menu hoạt động
91. **FE_ADMIN_03**: User management table với CRUD actions
92. **FE_ADMIN_04**: Company management với filter/search
93. **FE_ADMIN_05**: Role & Permission management UI
94. **FE_ADMIN_06**: Export data functionality

### 13. Recruiter Dashboard (6 cases)
95. **FE_RECRUIT_01**: Tạo công ty khi chưa có công ty
96. **FE_RECRUIT_02**: Job management cho công ty
97. **FE_RECRUIT_03**: CV/Resume management
98. **FE_RECRUIT_04**: Thêm/xóa thành viên công ty
99. **FE_RECRUIT_05**: Update công ty profile
100. **FE_RECRUIT_06**: View applicants cho từng job

### 14. User Profile & Settings (6 cases)
101. **FE_USER_01**: View và edit profile information
102. **FE_USER_02**: Change password với validation
103. **FE_USER_03**: Upload avatar với preview
104. **FE_USER_04**: View danh sách CV đã nộp
105. **FE_USER_05**: Subscribe/unsubscribe job notifications
106. **FE_USER_06**: Manage login sessions

## III. INTEGRATION TESTING (10+ cases)

107. **INT_01**: End-to-end flow: Register -> Login -> Apply Job
108. **INT_02**: Company create job -> User apply -> Review CV
109. **INT_03**: Admin create role -> Assign to user -> Check permissions
110. **INT_04**: Upload file -> Store in cloud -> Retrieve URL
111. **INT_05**: Send email notification khi có job mới
112. **INT_06**: Search với Vietnamese text (có dấu)
113. **INT_07**: Concurrent user sessions
114. **INT_08**: Rate limiting cho API calls
115. **INT_09**: Database transaction rollback on error
116. **INT_10**: Cache invalidation sau khi update data

## IV. PERFORMANCE TESTING (5 cases)

117. **PERF_01**: Load test với 100 concurrent users
118. **PERF_02**: API response time < 500ms cho GET requests
119. **PERF_03**: File upload với file 10MB
120. **PERF_04**: Database query optimization (no N+1)
121. **PERF_05**: Frontend bundle size < 500KB

## V. SECURITY TESTING (5 cases)

122. **SEC_01**: SQL Injection prevention
123. **SEC_02**: XSS attack prevention
124. **SEC_03**: CSRF protection
125. **SEC_04**: Rate limiting để prevent brute force
126. **SEC_05**: Sensitive data không expose trong API response
