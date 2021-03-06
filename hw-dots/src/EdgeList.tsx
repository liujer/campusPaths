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

interface EdgeListProps {
    onChange(edges: [[number, number], [number, number], string][]): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
    size: number;
}

interface EdgeListState {
    edges: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            edges: "",
        }
    }

    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const stringEdges = event.target.value;
        this.setState({edges: stringEdges});
    }

    parseData = () : [[number, number], [number, number], string][] => {
        const edgesArr = this.state.edges.split('\n');
        // regex searches for two coordinate pairs and a string
        const regex = /-?\d+,-?\d+\s+-?\d+,-?\d+\s+(.)*/;

        let result: [[number, number], [number, number], string][] = [];
        let lineNumber = 1;
        let alertMsg = ""; // track alerts

        for (let edge of edgesArr) {
            if (edge.match(regex)) {
                // Convert one line of data into array
                const temp = edge.split(/\s+/);
                const firstCoord = temp[0].split(',');
                const secondCoord = temp[1].split(',');
                let newEdge : [[number, number], [number, number], string] = [
                    [parseInt(firstCoord[0]), parseInt(firstCoord[1])],
                    [parseInt(secondCoord[0]), parseInt(secondCoord[1])],
                    temp[2]
                ];
                // Add array element if valid line of input
                let testInput = this.validateInput(newEdge, lineNumber);
                if (testInput == "") { // no errors from validation
                    result.push(newEdge);
                } else {
                    alertMsg = alertMsg.concat(testInput);
                }
            } else if (edge != "") { // skip empty lines, just incorrect output
                alertMsg = alertMsg.concat("Line " + lineNumber + ": Missing portion " +
                    "of line, or missing space\n");
            }
            lineNumber++;
        }
        if (alertMsg != "") { // errors happened
            alert(alertMsg);
        }
        return result; // array of all parsed and valid lines
    };

    validateInput = (edge : [[number, number], [number, number], string],
                     lineNumber: number) : string => {
        let lineString = "Line " + lineNumber + ": ";
        let alertMsg = ""; // track alerts
        // iterate through all coordinates in edge
        for (let i = 0; i < edge.length - 1; i++) {
            for (let j = 0; j < edge.length - 1; j++) {
                // add error warning if necessary
                if (edge[i][j] < 0) {
                    alertMsg = alertMsg.concat(lineString + "Point " + i + " has a negative coordinate\n");
                } else if (edge[i][j] > this.props.size) {
                    alertMsg = alertMsg.concat(lineString + "Point " + i + " has a coordinate " +
                        "higher than grid size\n");
                }
            }
        }
        return alertMsg;
    };

    draw = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        this.props.onChange(this.parseData());
    }

    clear = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        this.setState({edges: ""});
        this.props.onChange([]);
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.onInputChange}
                    value={this.state.edges}
                /> <br/>
                <button onClick={this.draw}>Draw</button>
                <button onClick={this.clear}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
