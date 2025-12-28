import {onMounted, onUnmounted, type Ref, ref} from 'vue';

export const vwDetector = ():Ref<boolean> =>{
    const isMobile = ref<boolean>(false);
    const resize = ():void => {
        if (typeof window !== 'undefined') {
            isMobile.value = window.innerWidth <= 767;
        }
    };
    onMounted(()=>{
        resize();
        window.addEventListener('resize',resize);
    })

    onUnmounted(()=>{
        window.removeEventListener('resize',resize);
    })

    return isMobile;
}
