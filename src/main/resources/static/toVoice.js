export function textToVoice(
    {
                                b = '#voice',
                                i = '#text',
                                defaultText = 'hello, thanks for using Betterpedia',
                                rate = 1, pitch = 1, volume = 1
    } = {}) {

    const btn = document.querySelector(b);
    const input = document.querySelector(i);

    function speak(text) {
        const t = (text ?? '').trim() || defaultText;

        if (speechSynthesis.speaking || speechSynthesis.pending) speechSynthesis.cancel();

        const utterance = new SpeechSynthesisUtterance(t);

        // english-only voice selection (inline; no extra function)
        const vs = speechSynthesis.getVoices();
        const voice =
            vs.find(v => v.lang && v.lang.toLowerCase() === 'en-us') ||
            vs.find(v => v.lang && v.lang.toLowerCase().startsWith('en-')) ||
            null;

        if (voice) { utterance.voice = voice; utterance.lang = voice.lang; }
        else { utterance.lang = 'en-us'; } // force english even if not found

        utterance.rate = rate; utterance.pitch = pitch; utterance.volume = volume;

        btn.disabled = true;
        const original = btn.textContent;
        btn.textContent = 'speaking ...';

        const reset = () => { btn.disabled = false; btn.textContent = original; };
        utterance.onend = reset;
        utterance.onerror = reset;

        speechSynthesis.speak(utterance);
    }

    // some browsers load voices asynchronously; prime the list
    speechSynthesis.addEventListener?.('voiceschanged', () => speechSynthesis.getVoices());

    btn.addEventListener('click', () => speak(input ? input.value : btn.dataset.text));
    return { speak };
}
