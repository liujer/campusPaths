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

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

interface GridSizePickerProps {
    value: string;                    // text to display in the text area
    onChange(newSize: number): void;  // called when a new size is picked
}

interface GridSizePickerState {
    currString: string;
}

class GridSizePicker extends Component<GridSizePickerProps, GridSizePickerState> {

    constructor(props: GridSizePickerProps) {
        super(props);
        this.state = {
            currString: "4",
        };
    }

    onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below.
        //
        const newSize: number = parseInt(event.target.value);
        this.setState({currString: event.target.value});
        if (event.target.value === "") {
            this.props.onChange(0);
        }
        else if (newSize <= 100 && newSize >= 0) {
            this.props.onChange(newSize); // Tell our parent component about the new size.
        } else {
            alert("Input is larger than 100 or lower than 0");
        }

    };

    render() {
        return (
            <div id="grid-size-picker">
                <label>
                    Grid Size:
                    <input
                        value={this.state.currString}
                        onChange={this.onInputChange}
                        type="number"
                        min={0}
                        max={100}
                    />
                </label>
            </div>
        );
    }
}

export default GridSizePicker;
