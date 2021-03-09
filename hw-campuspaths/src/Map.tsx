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
import "./Map.css";

interface MapProps {
    start: string,
    dest: string,
}
interface MapState {
    backgroundImage: HTMLImageElement | null,
    currPath: Path | null,
}

interface Path {
    cost: number,
    start: [number, number],
    path: Segment[],
}

interface Segment {
    cost: number,
    start: Point,
    end: Point,
}

interface Point {
    x: number,
    y: number
}

class Map extends Component<MapProps, MapState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapProps) {
        super(props);
        this.state = {
            backgroundImage: null,
            currPath: null,
        };
        this.canvas = React.createRef();


    }

    componentDidMount() {
        // Might want to do something here?
        this.fetchAndSaveImage();
        this.drawBackgroundImage();


    }

    componentDidUpdate(prevProps: Readonly<MapProps>, prevState : Readonly<MapState>) {
        // Might want something here too...
        if (prevProps.start !== this.props.start ||
            prevProps.dest !== this.props.dest) {
            this.sendRequest();
        }
        this.drawBackgroundImage();


    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //

        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        if (this.state.currPath !== null) {
            console.log(this.state.currPath);
            this.state.currPath.path.forEach( (segment) => {
                this.drawLine(ctx, segment.start, segment.end);
            })
        }

    }

    async sendRequest() {
        try {
            const url = 'http://localhost:4567/findPath?start=' + this.props.start
                + "&dest=" + this.props.dest;
            let response = await fetch(url);
            if (!response.ok) {
                alert("Unable to fetch data");
                return;
            }
            let parsed : Path = await response.json() as Path;

            this.setState({
                currPath: parsed
            });

        } catch (e: any) {
            console.log(e.message);
        }

    }

    drawLine = (ctx: CanvasRenderingContext2D | null, start: Point, end: Point) => {
        console.log(start);
        if (ctx != null) {
            ctx.lineWidth = 10;
            ctx.strokeStyle = "red";
            ctx.beginPath();
            ctx.moveTo(start.x, start.y);
            ctx.lineTo(end.x, end.y);
            ctx.stroke();
        }

    };

    render() {
        return (
            <div id="map">
                <canvas ref={this.canvas}/>
            </div>

        )
    }
}

export default Map;