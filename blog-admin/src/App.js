import React from "react";
import {Route, Router, hashHistory} from "react-router-dom";
import {LoginPage} from "./layout/LoginPage";
import {DashboardPage} from "./layout/DashboardPage";

class App extends React.Component {
    render() {
        return (
            <Router history={hashHistory}>
                <Route path="/" component={LoginPage}/>
                <Route path="/dashboard" component={DashboardPage}/>
            </Router>
        );
    }
}

export default App;
