`SubwayQuerySystem/
├── backend/                  # 后端代码
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── subway/
│   │   │   │           ├── config/         # 配置类
│   │   │   │           ├── controller/     # RESTful API控制器
│   │   │   │           ├── model/          # 数据模型
│   │   │   │           ├── service/        # 业务逻辑
│   │   │   │           ├── util/           # 工具类
│   │   │   │           └── SubwayApplication.java  # 主程序入口
│   │   │   └── resources/
│   │   │       ├── application.properties  # 配置文件
│   │   │       └── data/                   # 静态数据文件
│   │   │           ├── parsed_departure_times.json
│   │   │           ├── subway_lines_final.json  # 假设转为JSON格式
│   │   │           └── station_distance_final.json
│   │   └── test/
│   └── pom.xml               # Maven依赖文件
├── frontend/                 # 前端代码
│   ├── css/
│   │   └── style.css         # 样式文件
│   ├── js/
│   │   └── app.js            # 前端逻辑
│   └── index.html            # 主页面
└── README.md                 # 项目说明`