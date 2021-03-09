/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import DropdownMenu from "./DropdownMenu";
import Map from "./Map";
import "./App.css";

interface AppState {
    start: string,
    dest: string,
}

class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            start: "PAR",
            dest: "PAR",
        };
    }

    render() {
        return (

            <div id="app-div">
                <style>
                    @import url('https://fonts.googleapis.com/css2?family=Aleo&display=swap');
                </style>
                <div id="title">Campus Paths</div>
                <DropdownMenu onChange={(start: string, dest: string) => {
                    this.setState({
                        start: start,
                        dest: dest,
                    })
                }}>Find Path</DropdownMenu>
                <Map start={this.state.start} dest={this.state.dest}>Campus Map</Map>
            </div>
        );
    }

}

export default App;
