/**
 * Test in https://leetcode.com/problems/integer-break/
 */

/**
 * 6
 * 5 + 1 -> 5*1, f(5)*1
 *   4 + 1 -> 4*1, f(4)*1
 *   3 + 2 -> ...
 * 4 + 2 -> 4*2, f(4)*2
 *   3 + 1 -> ...
 *   2 + 2 -> ...
 * 3 + 3
 *   ...
 */

function f(n) { // O(N^2)
    if (n == 2) return 1; // 2 -> 1+1 -> 1*1 -> 1 (base case)

    const mid = Math.ceil(n / 2); // n \ 2

    let max = 0;
    for (let k = n - 1; k >= mid; k--) { // k in [n-1..n\2] -> O(N/2) -> O(N)
        const i = n - k; // sum complement
        max = Math.max(max, f(k) * i, k * i); // f() is called ~(k-1)/2 times, k grows with N -> O(N)
    }

    return max;
}

function f_DP(n) { // O(N)
    const dp = Array.from({ length: n }, () => 0);
    return f_DP_recursive(n, dp);
}

function f_DP_recursive(n, dp) { // O(N)
    if (n == 2) return 1;
    if (dp[n]) return dp[n]; // dynamic progamming

    const mid = Math.ceil(n / 2);
    let max = 0;
    for (let k = n - 1; k >= mid; k--) { // O(N/2) -> O(N)
        const i = n - k;
        max = Math.max(max, f_DP_recursive(k, dp) * i, k * i); // O(1)
    }

    dp[n] = max; // dp[2] = 2

    return max;
}

// f(2) -> 1 // O(1)
// f(3) -> max{ f(2)*1, 2*1 } // O(1)
// f(4) -> max{ f(3)*1, 3*1, f(2)*2, 2*2 } // O(1)
// f(5) -> max{ f(3)*2, ...}
// ...
// by induction
// f(n) = max{ f(n-k)*k, (n-k)*k }, for k in 2..n, n>2
// f(2) = 1 (base case)

// 3 = 2 + 1 -> 2 * 1 -> 2
// 4 = 2 + 2 -> 4
// 10 = 3 + 3 + 4 -> 36
console.log('RESULT =', f(10)); // 36
console.log('RESULT =', f_DP(10)); // 36

// Challenge: solve the problem bottom-up