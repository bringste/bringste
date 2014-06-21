//
//  TOMSViewController.m
//  bringste
//
//  Created by Tom König on 21/06/14.
//  Copyright (c) 2014 TomKnig. All rights reserved.
//

#import "TOMSViewController.h"
#import "Constants.h"

@interface TOMSViewController () <UIWebViewDelegate>
@property (weak, nonatomic) IBOutlet UIWebView *webView;
@property (assign, getter = isLoaded) BOOL loaded;
@end

@implementation TOMSViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.view setBackgroundColor:[UIColor colorWithRed:0 green:0.639 blue:0.769 alpha:1]];
    
    self.webView.alpha = 0;
    self.webView.delegate = self;
    
    NSURL *url = [NSURL URLWithString:kBringsteURL];
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
    [self.webView loadRequest:request];
}

#pragma mark - Load indication

- (void)loadingDidStart
{
}

- (void)loadingDidEnd
{
}

#pragma mark - UIWebViewDelegate

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    if (!self.isLoaded) {
        [self loadingDidStart];
    }
}

- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    if (!self.isLoaded) {
        [self loadingDidEnd];
        [UIView animateWithDuration:0.37
                         animations:^{
                             self.webView.alpha = 1;
                         }];
        self.loaded = YES;
    }
}

@end
