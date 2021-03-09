import React, {ChangeEvent, Component} from 'react';

interface DropdownMenuProps {
    onChange(start: string, dest: string) : void,
}

interface DropdownMenuState {
    data: Record<string, string>,
    currStart: string,
    currDest: string,
}


class DropdownMenu extends Component<DropdownMenuProps, DropdownMenuState> {

    constructor(props: DropdownMenuProps) {
        super(props);
        this.state = {
            data: {},
            currStart: "Parrington Hall",
            currDest: "Parrington Hall",
        }
        this.sendRequest();
    };

    async sendRequest() {
        try {
            let response = await fetch('http://localhost:4567/');
            if (!response.ok) {
                alert("Unable to fetch data");
                return;
            }
            let parsed : Record<string, string> = await response.json() as
                Record<string, string>;
            this.setState({
                data: parsed
            });
        } catch (e: any) {
            alert(e.message);
        }
    };

    getShortName = (longName: string) : string => {
        return Object.keys(this.state.data).find(key =>
            this.state.data[key] === longName) as string;
    }

    getPathPoints = () => {
        const startShort = this.getShortName(this.state.currStart);
        const destShort = this.getShortName(this.state.currDest);
        this.props.onChange(startShort, destShort);
    };

    clear = () => {
        this.setState({
            currStart: "Parrington Hall",
            currDest: "Parrington Hall"
        })
        this.props.onChange("PAR", "PAR");
    }

    render() {
        return (
            <div id="DropdownMenu"> From:
                <select className="input" key="start" value={this.state.currStart} onChange={(event) => {
                    this.setState({currStart: event.target.value})}}>
                    {Object.values(this.state.data).map((value) => <option className="input" key={value}>{value}</option>)}
                </select>
                To:
                <select className="input" key="dest" value={this.state.currDest} onChange={(event) => {
                    this.setState({currDest: event.target.value})}}>
                    {Object.values(this.state.data).map((value) => <option className="input" key={value}>{value}</option>)}
                </select>
                <button className="input" onClick={this.getPathPoints}>Find Path</button>
                <button className="input" onClick={this.clear}>Clear</button>
            </div>

        );
    }

}

export default DropdownMenu;