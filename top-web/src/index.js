import dva from 'dva';
import createLoading from 'dva-loading';
import './index.js';
import './index.css';

// 1. Initialize
const app = dva();

// 2. Plugins
app.use(createLoading());

// 3. Model
app.model(require('./models/blog'));

app.model(require("./models/createArticle"));

app.model(require("./models/search"));

app.model(require("./models/home"));

app.model(require("./models/login"));

app.model(require("./models/Top"));

// 4. Router
app.router(require('./router'));

// 5. Start
app.start('#root');