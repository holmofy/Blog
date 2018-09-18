import React from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import {DashboardPage} from "./layout/DashboardPage";

class App extends React.Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path="/" component={DashboardPage}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
