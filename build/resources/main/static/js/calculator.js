function handleButton(button) {
    // フォームを作成してボタンクリックを送信
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/button';
    
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'button';
    input.value = button;
    
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}

// キーボードイベントの処理
document.addEventListener('keydown', function(event) {
    const key = event.key;
    
    // 数字キー
    if (key >= '0' && key <= '9') {
        handleButton(key);
        event.preventDefault();
    }
    
    // 演算子キー
    switch (key) {
        case '+':
            handleButton('+');
            event.preventDefault();
            break;
        case '-':
            handleButton('-');
            event.preventDefault();
            break;
        case '*':
            handleButton('*');
            event.preventDefault();
            break;
        case '/':
            handleButton('/');
            event.preventDefault();
            break;
        case 'Enter':
        case '=':
            handleButton('=');
            event.preventDefault();
            break;
        case 'Escape':
            handleButton('C');
            event.preventDefault();
            break;
        case '.':
            handleButton('.');
            event.preventDefault();
            break;
        case '%':
            handleButton('%');
            event.preventDefault();
            break;
    }
});
