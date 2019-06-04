export class MenuItem {
    constructor(
        public text: string,
        public link: string,
        public icon: string,
        public action: () => void = () => false
    ) {
    }

}
